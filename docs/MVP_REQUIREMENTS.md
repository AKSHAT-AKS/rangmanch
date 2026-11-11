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
- As a registered user, I can rate a title.
- As a registered user, I can write, edit, and delete my own review.
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

## 8. Open technical decisions

These decisions should be made before the implementation begins:

- Backend and frontend framework.
- Database and migration tool.
- External movie/series metadata provider and its terms.
- Authentication approach.
- Deployment target.

Do not hard-code an external API key. Keep provider integration behind a small service interface so it can be replaced later.
