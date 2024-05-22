# Telegram-bot component

![readme_logo.png](resources%2Freadme_logo.png)

# Table of content

1. [Introduction](#Introduction)
2. [Usage](#Usage)

# Introduction

This component will act as frontend for a Mini-Bank project. Whole system will consist of three main components:

* Frontend Layer: A Telegram bot developed using Kotlin.
* Middle Layer: A Kotlin service that receive requests from bot, validates and process them and afterward send them to backend.
* Backend Layer: A Kotlin service that handles data storage, transaction processing, etc.

![bot-info.png](resources%2Fbot-info.png)

# Usage

To run in locally you need to insert your bot name and token into [application.yml file](zolbot%2Fsrc%2Fmain%2Fresources%2Fapplication.yml).

Then type `./gradlew bootRun`
