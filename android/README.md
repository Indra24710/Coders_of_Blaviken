<h1 align="center"> Jatayu Criminal Tagging </h1> <br>
<p align="center">
  <a href="http://jatayu.org/">
    <img alt="Jatayu" title="Jatayu" src="https://i.imgur.com/RKlSJO7.png" width="300">
  </a>
</p>

<p align="center">
  Detection data, alerts and live gps tracking in one place.
</p>

<p align="center">
  <!-- <a href="https://itunes.apple.com/us/app/gitpoint/id1251245162?mt=8">
    <img alt="Download on the App Store" title="App Store" src="http://i.imgur.com/0n2zqHD.png" width="140">
  </a> -->

  <a href="https://drive.google.com/file/d/1jf2gF54voIMy7eVFV71OjMqzVqdjYPup/view">
    <img alt="Get it on Google Drive" title="Google Drive" src="https://file.wiki/wp-content/uploads/2018/10/Download-Google-Drive-File-Stream-Offline-Installer-1.png" width="140">
  </a>
</p>

<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->
## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Contributors](#contributors)
- [Build Process](#build-process)
- [Acknowledgments](#acknowledgments)

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

## Introduction

<!-- [![Build Status](https://img.shields.io/travis/gitpoint/git-point.svg?style=flat-square)](https://travis-ci.org/gitpoint/git-point)
[![Coveralls](https://img.shields.io/coveralls/github/gitpoint/git-point.svg?style=flat-square)](https://coveralls.io/github/gitpoint/git-point)
[![All Contributors](https://img.shields.io/badge/all_contributors-73-orange.svg?style=flat-square)](./CONTRIBUTORS.md)
[![PRs Welcome](https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square)](http://makeapullrequest.com)
[![Commitizen friendly](https://img.shields.io/badge/commitizen-friendly-brightgreen.svg?style=flat-square)](http://commitizen.github.io/cz-cli/)
[![Gitter chat](https://img.shields.io/badge/chat-on_gitter-008080.svg?style=flat-square)](https://gitter.im/git-point) -->

Access data from the criminal detection database and also track suspects using realtime GPS tracking. Alert other officers of the city using the alert feature and many other incredible features.

**Available for Android.**

<p align="center">
  <img src = "https://i.imgur.com/wLR0t2q.jpg" width=350 style="border-radius:5%;">
</p>

## Features

A few of the things you can do with Jatayu:

* Login securely using biometric auth.
* Click and upload doubtful scenarios and suspects.
* Alert the city police about wanted suspects.
* Track on-alert suspects
* Preview criminal data
* Search for detections all across the city.
* Available in mutiple languages
* Easily search for any criminal or detection

<p align="center">
  <img src = "https://i.imgur.com/qK5LRqx.png" width=700 style="border-radius:5%;">
</p>


## Contributors

* Niveth Saran V J
* Indra Kumar V
* Srinath R
* Sneha S S
* Keshav Ramaiah
* Saiharsha Balasubramaniam

## Build Process

- Clone or download the repo
- Use Android Studio to import the project as a android app
- Create a account in firebase and enable FCM and Firebase Auth
- Download firebase google-services.json file and save it in the app directory
- Ensure that the app and firebase are connected
- Create a maps api key and integrate it into the app
- Create a AVD
- Run the app
- Use logcat to log and debug errors in the application
<!-- 
Please take a look at the [contributing guidelines](./CONTRIBUTING.md) for a detailed process on how to build your application as well as troubleshooting information. -->
<!-- 
**Development Keys**: The `CLIENT_ID` and `CLIENT_SECRET` in `api/index.js` are for development purposes and do not represent the actual application keys. Feel free to use them or use a new set of keys by creating an [OAuth application](https://github.com/settings/applications/new) of your own. Set the "Authorization callback URL" to `gitpoint://welcome`. -->
<!-- 
## Backers [![Backers on Open Collective](https://opencollective.com/git-point/backers/badge.svg)](#backers)

Thank you to all our backers! 🙏 [[Become a backer](https://opencollective.com/git-point#backer)]

<a href="https://opencollective.com/git-point#backers" target="_blank"><img src="https://opencollective.com/git-point/backers.svg?width=890"></a>

## Sponsors [![Sponsors on Open Collective](https://opencollective.com/git-point/sponsors/badge.svg)](#sponsors)

Support this project by becoming a sponsor. Your logo will show up here with a link to your website. [[Become a sponsor](https://opencollective.com/git-point#sponsor)]

<a href="https://opencollective.com/git-point/sponsor/0/website" target="_blank"><img src="https://opencollective.com/git-point/sponsor/0/avatar.svg"></a>
<a href="https://opencollective.com/git-point/sponsor/1/website" target="_blank"><img src="https://opencollective.com/git-point/sponsor/1/avatar.svg"></a>
<a href="https://opencollective.com/git-point/sponsor/2/website" target="_blank"><img src="https://opencollective.com/git-point/sponsor/2/avatar.svg"></a>
<a href="https://opencollective.com/git-point/sponsor/3/website" target="_blank"><img src="https://opencollective.com/git-point/sponsor/3/avatar.svg"></a>
<a href="https://opencollective.com/git-point/sponsor/4/website" target="_blank"><img src="https://opencollective.com/git-point/sponsor/4/avatar.svg"></a>
<a href="https://opencollective.com/git-point/sponsor/5/website" target="_blank"><img src="https://opencollective.com/git-point/sponsor/5/avatar.svg"></a>
<a href="https://opencollective.com/git-point/sponsor/6/website" target="_blank"><img src="https://opencollective.com/git-point/sponsor/6/avatar.svg"></a>
<a href="https://opencollective.com/git-point/sponsor/7/website" target="_blank"><img src="https://opencollective.com/git-point/sponsor/7/avatar.svg"></a>
<a href="https://opencollective.com/git-point/sponsor/8/website" target="_blank"><img src="https://opencollective.com/git-point/sponsor/8/avatar.svg"></a>
<a href="https://opencollective.com/git-point/sponsor/9/website" target="_blank"><img src="https://opencollective.com/git-point/sponsor/9/avatar.svg"></a>

-->

## Modules

<p>The app is divided into 4 packages namely</p>

- Activities
- Fragments
- Adapters
- Services
- Utils

<p>Activities and Fragments contain backend code for connecting frontend with backend. Adapters contain the code for all lists and recycler views. Services contains code which deals with all background processes and notification handling for alerts. Utils package has basic code utilities for checking internet connection, FCM Tasks etc.</p>


## Acknowledgments

Thanks to [Smart India Hackathon](https://www.sih.gov.in/) for supporting us by giving us this wonderful oppurtunity and mentoring us to become better developers.