# Rangmanch

A social platform for discovering movies and television shows, sharing opinions, and finding people with similar viewing interests.

## Product direction

Rangmanch is intended to make entertainment discovery more social. Instead of relying only on ratings and recommendation lists, users should be able to keep track of what they watch and exchange recommendations with others.

## Planned core features

The first version will focus on a small, usable set of features:

- Browse and search for movies and shows.
- View a title page with basic details.
- Filter the catalogue by movie or series.
- Save titles to a session-based watchlist and remove them later.
- Submit a 1–5 rating and a short written review on a title page.
- Create a personal watchlist.
- Mark titles as watched or currently watching.
- Rate titles and write short reviews.
- Explore reviews and recommendations from other users.

## Development stages

| Stage | Focus | Status |
|---|---|---|
| 1 | Define the product scope and user flows | Complete |
| 2 | Choose the application stack and set up the project | Complete |
| 3 | Build title browsing and detail pages | Complete |
| 4 | Add watchlists and user activity | In progress |
| 5 | Add ratings and reviews | In progress |
| 6 | Add tests and documentation | In progress |

## Design principles

- Keep the interface focused on discovering and discussing entertainment.
- Prefer simple user flows over unnecessary features.
- Separate title information, user activity, and social interactions.
- Add features incrementally and cover important behaviour with tests.

## Current status

The initial catalogue application is implemented with Java 21, Spring Boot, Thymeleaf, Spring Data JPA, and an H2 file database. It includes searchable sample movie/series records and title detail pages. The catalogue currently uses local seed data. Users can add and remove titles from a browser-session watchlist, track each title as plan to watch, currently watching, or watched, and submit a display-name review with a 1–5 rating. Watchlist entries and statuses are cleared when the session ends. External metadata integration, registered accounts, persistent per-user watchlists, watched/currently-watching status, review editing/deletion, moderation, and broader social discovery remain future work.

**Development safety:** Reviews are currently anonymous apart from a user-entered display name and are not moderated. Do not expose this prototype publicly until authentication, authorization, abuse prevention, and moderation are implemented.

## Contributing and development

Work should be organised around small features or fixes. Each change should include a clear commit message, and larger changes should be developed on a feature branch and reviewed through a pull request.

## License

No license has been selected yet. Until one is added, the source is not explicitly offered for reuse or redistribution.
