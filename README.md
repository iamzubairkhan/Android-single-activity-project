# Android Developer Assignment - Svea Apps

In this repository, you will find a gist for what we believe, over time, could turn
into a massive activity. Your task is to break this view into logical, testable
chunks.

You can use a project structure of your choosing, including 3rd party dependencies and other
design choices. You may also choose the expand it or modify it's layout

## Specs

The activity in this example lists places fetched from a REST API and presents it to
the user, once a button is clicked.

### Think about these things when you design your solution

* Testability
* Dependencies
* Separation of responsibilities
* Readability
* Maintainability


You will receive feedback on your work regardless of where you are in the
recruitment process.

## Data models

### Places response

The places response is a json object with the following structure:

```json
{
    "place": "array",
    "total": "number"
}
```

### Place

A place is a json object with the following structure:

```json
{
    "alias": "string",
    "name": "string",
    "longitude": "number",
    "latitude": "number",
    "description": "string",
    "icon": "string",
    "website": "string"
}
```
