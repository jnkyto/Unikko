# Changelog

All notable changes to this project will be documented in this file. The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).

## Unreleased (1.1.0-alpha.5) - 2021/11/18

Note: This version should be ready for release, but I have yet to test it comprehensively.

### Added
 - Changelog
 - Lag meter
 - TPS counter
 - Japanese translation (unfinished)

### Changed
 - SoftConfig is now object-based, e.g. no more static methods. This is somewhat of a big update, so bugs may occur.
 - Help command now lists all commands with hover events providing additional info.
 - Translation file updates mostly related to aforementioned change.
 - Nbt command now has a print limit of 4096 characters.
 - Homepage has been updated in fabric.mod.json
 - Made Unikko adhere to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).
 - Improved toggling of detailed DiscordRPC
 - README file updates
 - Bumped dependencies.