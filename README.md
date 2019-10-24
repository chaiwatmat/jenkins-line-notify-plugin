# Jenkins Line Notify

This plugin use for send message to LINE application, using service [Line notify](https://notify-bot.line.me/en/)

## Prerequisite

- LINE account
- Token from [Line notify](https://notify-bot.line.me/en/) 

## Installation

Install plugin `line-notify-plugin` from `Jenkins > Manage Jenkins > Manage Plugins`

## How to use

### Freestyle job

Just add `Line notify` from build step

### Example Pipeline job

```shell script
node {
   lineNoti groupName: 'my group', lineToken: '*****', message: 'hello from Jenkins'
}
```
