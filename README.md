# Git_UI_Api_Tests

Test automation project for GitHub's repository search — covers both the web UI (github.com) and the REST API (`api.github.com`). Built with Selenium, REST-Assured and TestNG, following a Page Object Model on the UI side and a thin API client on the API side.

## Tech stack

- Java 17
- Gradle (wrapper included, no local Gradle install required)
- Selenium 4.47.0 (uses Selenium Manager — no manual ChromeDriver download needed)
- TestNG 7.12.0
- REST-Assured 5.5.7
- Jackson Databind 2.22.1
- networknt json-schema-validator 1.5.6
- Lombok

## Prerequisites

- JDK 17 or newer
- Google Chrome installed (Selenium Manager will automatically download a matching ChromeDriver version the first time you run the UI test — no manual setup needed)

## Getting started

```
git clone <this-repo-url>
cd Git_UI_Api_Tests
```

## Running the tests

**Windows (PowerShell)** — note the `.\` prefix; PowerShell won't run scripts from the current folder without it:
```
.\gradlew.bat test
```

**Windows (cmd.exe):**
```
gradlew.bat test
```

**macOS / Linux:**
```
./gradlew test
```

### Running a single test class

```
gradlew.bat test --tests "api_tests.FindRepoAPITests"
gradlew.bat test --tests "ui_tests.FindRepoTests"
```

### Running against a different repository

Both tests default to searching for this project's own repository. Override it at runtime with the `repoName` system property:

```
gradlew.bat test -DrepoName="owner/repository-name"
```

**Important:** the API test (`FindRepoAPITests`) requires the **full** `owner/repository-name` format — it's passed to GitHub's `repo:` search qualifier, which only matches an exact repository and returns nothing for a partial name. The UI test (`FindRepoTests`) is more forgiving and also accepts a short/partial name, since it performs a plain-text search rather than an exact lookup.

## Design notes

- **UI search pagination is capped at 5 pages.** Navigating deep into GitHub's search result pages (via repeated "Next" clicks, or by jumping directly to a page number) triggers a `429 Too Many Requests` / abuse-detection response from GitHub, regardless of how many total pages exist. To stay safely within that limit, the UI test only ever visits up to 5 pages of results.
- If the searched term returns more results than can be verified within those 5 pages, the "avatar count matches displayed total" check is skipped (not failed) — that number can't be verified exactly without unsafe deep pagination.
- If GitHub displays an abbreviated result count (e.g. `"1.5k results"`), the test is skipped entirely (`SkipException`) rather than failed, since GitHub doesn't expose an exact number in that case — it's known and documented that the GitHub Search API itself caps results at 1,000 regardless of the reported total.
- The API test asserts the search returns exactly one exact result, since it uses GitHub's `repo:owner/name` qualifier, which — per GitHub's own documentation — matches only that specific repository, never similarly named ones.

## Project structure

```
src/main/java/
  api_rest/    GitHub REST API client
  dto/         Response DTOs (Jackson-mapped)
  manager/     WebDriver lifecycle (setup/teardown)
  pages/       Page Objects for github.com
  utils/       Shared constants, JSON schema validation, test data

src/test/java/
  api_tests/   API test(s)
  ui_tests/    UI test(s)

src/test/resources/
  schema.json  JSON schema for the search-repositories API response
```

## Troubleshooting

**ChromeDriver version mismatch / "session not created" error** — don't install ChromeDriver manually. If an old copy is on your system `PATH`, remove it; Selenium Manager (built into Selenium 4.6+) resolves and downloads the correct version automatically based on your installed Chrome.

**`429` / "Too many requests" from github.com** — GitHub's rate limiting, usually from running the UI test many times in a short period. Wait a while before retrying.

**PowerShell says `gradlew.bat` is not recognized** — PowerShell doesn't run scripts from the current directory by default; use `.\gradlew.bat` instead of `gradlew.bat`.
