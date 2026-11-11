# Rangmanch — MVP Product Requirements

## 1. Purpose

Rangmanch is a social discovery space for movies and television shows. The first release should help a user keep a watchlist, record what they have watched, and discover titles through community opinions.

## 2. Target users

- People who regularly watch films or series and want one place to track them.
- Users who rely on reviews and recommendations from people with similar tastes.
- Small groups of friends who want to exchange watch suggestions.

## 3. MVP user stories

### Catalogue
- As a visitor, I can search for a movie or show by title.
- As a visitor, I can open a title page and see its basic metadata.
- As a visitor, I can distinguish movies from television shows.

### Personal tracking
- As a registered user, I can add a title to my watchlist.
- As a registered user, I can mark a title as watched or currently watching.
- As a registered user, I can remove a title from my list.

### Community
- As a visitor, I can submit a display-name rating for a title in the prototype.
- As a visitor, I can submit a short review in the prototype. Editing and deleting reviews require accounts and are deferred.
- As a visitor, I can read public reviews.

## 4. MVP boundaries

The first release will not include:
- Streaming or downloading content.
- Ticket sales or subscription payments.
- Private messaging or a real-time chat system.
- Algorithmic recommendations based on a large user profile.
- Complex moderation workflows.

These features can be reconsidered after the basic discovery and tracking flows work reliably.

## 5. Core data concepts

The initial domain model is expected to contain:

- **User** — account identity and profile information.
- **Title** — a movie or series, including external catalogue identifier and metadata.
- **WatchlistEntry** — user, title, status, and date added.
- **Review** — user, title, rating, text, and timestamps.

A user should have at most one watchlist entry per title and at most one active review per title. Database constraints should enforce these rules where practical.

## 6. Quality requirements

- Validate user input on the server.
- Keep user-owned watchlist and review actions restricted to their owner.
- Store secrets in environment variables, not source files.
- Handle missing catalogue metadata gracefully.
- Keep the first version responsive on mobile and desktop.
- Add automated tests for domain rules and important web flows.

## 7. Suggested delivery sequence

1. Select the application stack and catalogue metadata source.
2. Create the application skeleton and database model.
3. Implement catalogue search and title details.
4. Implement account registration and login.
5. Implement watchlist state changes.
6. Implement ratings and reviews.
7. Add integration tests, deployment configuration, and user documentation.

## 8. Current implementation decisions

- **Backend and UI:** Java 21, Spring Boot 3.5, Spring MVC, and Thymeleaf.
- **Persistence:** Spring Data JPA with H2 for local development; PostgreSQL is available as a runtime dependency for a later deployment profile.
- **Catalogue source:** local seed data for the first working version. No external provider or API key is required yet.
- **Watchlist:** browser-session storage for the prototype; it is not persistent across sessions or devices.
- **Reviews:** stored in the database with a display name, 1–5 rating, body, and creation timestamp. Reviews are not associated with verified accounts and have no moderation controls.
- **Authentication and deployment:** deliberately deferred. The prototype must not be exposed publicly with untrusted submissions until authentication, authorization, rate limiting, and moderation are implemented.

Keep any future metadata provider behind a service interface and store API credentials in environment variables, never in source files.

## 9. Remaining MVP work

1. Add registration and login.
2. Persist watchlist entries per user, including planned/watching/watched status.
3. Associate reviews with authenticated users and support editing/deletion.
4. Add abuse prevention and moderation controls.
5. Choose and integrate a metadata provider after reviewing its terms.
6. Add database migrations and deployment configuration.
