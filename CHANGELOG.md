# Changelog

All notable changes to this project will be documented in this file. The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).

## 1.1.1-alpha.4 - 2022/02/02

### Changed
- Fixed broken DiscordRPC and wrote it better (sort of)
- Bumped dependencies
- Refactoring

## Release 1.1.0 - 2021/11/21

Note: Japanese translation is not finished, it will be in 1.1.1. Still releasing this version as-is because of upcoming uni stuff and not making a release in like three months.

### Added
 - Changelog
 - Lag meter
 - TPS counter
 - Japanese translation

### Changed
 - SoftConfig is now object-based, e.g. no more static methods. This is somewhat of a big update, so bugs may occur.
 - Help command now lists all commands with hover events providing additional info.
 - Translation file updates mostly related to aforementioned change.
 - Nbt command now has a print limit of 4096 characters.
 - Homepage has been updated in fabric.mod.json
 - Made Unikko adhere to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).
 - Improved toggling of detailed DiscordRPC
 - Bumped dependencies.
 - README file updates
 - Refactoring