#!/usr/bin/env bash

scp -r -P 9922 ./out/artifacts/RL2_jar/RL2.jar epic@build.playmonumenta.com:project_epic/server_config/plugins/Roguelite.jar
