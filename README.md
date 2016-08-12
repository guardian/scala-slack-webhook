#Scala Slack Webhook

[![CircleCI](https://circleci.com/gh/guardian/scala-slack-webhook.svg?style=svg)](https://circleci.com/gh/guardian/scala-slack-webhook)

This is a scala library for pushing incoming webhooks to Slack

# Installation

Requires Scala 2.11

Add

```scala
resolvers += "Guardian Bintray" at "https://dl.bintray.com/guardian/editorial-tools"
```

and

```scala
libraryDependencies += "com.gu" %% "scala-slack-webhook" % "0.1.0"
```

to your build.sbt file

#Usage

You must already have a created a custom incoming webhook integration for your Slack account, via https://<your account>.slack.com/apps/build/custom-integration. You will need your Webhook URL, provided by Slack

Then in your codebase, instantiate a new webhook:

```scala
new SlackIncomingWebHook("<your webhook url>")
```

Create a payload for your post:

```scala
val payload = Payload("<your message text here>")
```

Text for your payload is mandatory. All other options will use the defaults for your Slack integration, however you can add additional parameters to override these:

```scala
val payload = Payload("<your message text here>").withChannel("your channel here")
```

Optional parameters are channel, iconUrl, iconEmoji and Attachments. The Payload has a 'with...' extension for each parameter

Attachments are also supported, as a Parameter for the Payload, however must be instantiated as a new object. Details of attachment and required fields can be found [here](https://api.slack.com/docs/message-attachments) They work in the same way as Payloads eg:

```scala
val payload = Payload("<your message text here>").withAttachment(Attachment(<params>))
```

# Publishing a new version

Once you've made some improvements  you need to publish a new version to make it available to all users.

## You will need a Bintray account!
In order to publish a new version of this schema you'll need a Bintray account.

1. Go to `bintray.com` and login with your GitHub account.
2. Someone will need to invite you to The Guardian Bintray org. Ask super nicely and they just might do it. They should also make you an admin.
3. You will need an API key.
  1. Go to your profile
  2. Click the Edit button near your profile name (top left).
  3. At the bottom of the list on the left will be the API key section containing your key. Keep this key handy for the next step.
4. Setup your username/API key locally.
  1. In this project run `sbt bintrayChangeCredentials`
  2. Enter your username and API key as prompted.
  3. This will save your creds locally and you shouldn't need to change them unless you refresh your API key.

## How to publish a new version
So you've made some changes and you want to publish a new version of this schema as a package to Bintray jcenter...

1. Make your changes
2. Bump the version in `version.sbt`. It's useful to follow the [SemVer guidelines](http://semver.org/) (see the summary section).
3. Ensure the project builds.
4. Run `sbt publish`
5. If you setup your Bintray account correctly then this should publish your new version to Bintray!
6. If for some reason you want to remove your package from Bintray you can run `sbt bintrayUnpublish` which will remove the package *at the current version*. Be careful not to unpublish versions other people are actively using!
