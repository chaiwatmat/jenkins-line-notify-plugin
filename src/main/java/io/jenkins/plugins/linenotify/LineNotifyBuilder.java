package io.jenkins.plugins.linenotify;

import hudson.Launcher;
import hudson.Extension;
import hudson.FilePath;
import hudson.util.FormValidation;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.Builder;
import hudson.tasks.BuildStepDescriptor;
import okhttp3.*;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import javax.servlet.ServletException;
import java.io.IOException;
import jenkins.tasks.SimpleBuildStep;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundSetter;

public class LineNotifyBuilder extends Builder implements SimpleBuildStep {

    private final String message;
    private String groupName;
    private String lineToken;

    @DataBoundConstructor
    public LineNotifyBuilder(String message, String groupName, String lineToken) {
        this.message = message;
        this.groupName = groupName;
        this.lineToken = lineToken;
    }

    public String getMessage() {
        return this.message;
    }

    public String getGroupName(){
        return this.groupName;
    }

    public String getLineToken(){
        return this.lineToken;
    }

    @Override
    public void perform(Run<?, ?> run, FilePath workspace, Launcher launcher, TaskListener listener) throws InterruptedException, IOException {
        listener.getLogger().println("Send message: " + message + "!");

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "message=" + this.message);
        Request request = new Request.Builder()
                .url("https://notify-api.line.me/api/notify")
                .post(body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("authorization", "Bearer " + this.lineToken)
                .build();

        Response response = client.newCall(request).execute();
    }

    @Symbol("lineNotify")
    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

        public FormValidation doCheckName(@QueryParameter String value, @QueryParameter String groupName, @QueryParameter String lineToken)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error(Messages.LineNotifyBuilder_DescriptorImpl_errors_missingMessage());
            return FormValidation.ok();
        }

        @Override
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return Messages.LineNotifyBuilder_DescriptorImpl_DisplayName();
        }

    }

}
