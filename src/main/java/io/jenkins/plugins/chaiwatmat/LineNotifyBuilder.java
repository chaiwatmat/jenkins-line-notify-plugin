package io.jenkins.plugins.chaiwatmat;

import hudson.Launcher;
import hudson.Extension;
import hudson.FilePath;
import hudson.util.FormValidation;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.Builder;
import hudson.tasks.BuildStepDescriptor;
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

    @DataBoundConstructor
    public LineNotifyBuilder(String message) {
        this.message = message;
    }

    @DataBoundSetter
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getMessage() {
        return this.message;
    }

    public String getGroupName(){
        return this.groupName;
    }

    @Override
    public void perform(Run<?, ?> run, FilePath workspace, Launcher launcher, TaskListener listener) throws InterruptedException, IOException {
        listener.getLogger().println("Send message: " + message + "!");
    }

    @Symbol("lineNoti")
    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {

        public FormValidation doCheckName(@QueryParameter String value)
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
