# Contributing to REanim

Thank you for your interest in contributing to REanim! This document provides guidelines and instructions for contributing to the project.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Git
- IntelliJ IDEA or Eclipse (recommended)

### Setting Up Development Environment

1. Fork the repository on GitHub
2. Clone your fork locally:
   ```bash
   git clone https://github.com/YOUR_USERNAME/REanim.git
   cd REanim
   ```

3. Set up the development environment:
   ```bash
   ./gradlew setupDecompWorkspace
   ./gradlew idea  # For IntelliJ IDEA
   # OR
   ./gradlew eclipse  # For Eclipse
   ```

4. Build the project:
   ```bash
   ./gradlew build
   ```

## How to Contribute

### Reporting Bugs

If you find a bug, please create an issue on GitHub with:
- A clear, descriptive title
- Steps to reproduce the bug
- Expected behavior vs. actual behavior
- Your Minecraft version and mod version
- Any relevant logs or screenshots

### Suggesting Features

Feature suggestions are welcome! Please create an issue with:
- A clear description of the feature
- Use cases and benefits
- Any implementation ideas you may have

### Submitting Pull Requests

1. Create a new branch for your feature or bugfix:
   ```bash
   git checkout -b feature/your-feature-name
   ```

2. Make your changes and commit them with clear, descriptive messages:
   ```bash
   git commit -m "Add feature: description of feature"
   ```

3. Push to your fork:
   ```bash
   git push origin feature/your-feature-name
   ```

4. Create a pull request on GitHub

### Code Style

- Follow Java naming conventions
- Use meaningful variable and method names
- Add comments for complex logic
- Keep methods focused and concise
- Write unit tests for new features when applicable

### Testing

Before submitting a pull request:
- Ensure the project builds without errors: `./gradlew build`
- Test your changes in a Minecraft client
- Verify no existing functionality is broken

## Code of Conduct

- Be respectful and constructive
- Welcome newcomers and help them learn
- Focus on what is best for the community
- Show empathy towards other contributors

## Questions?

If you have questions, feel free to:
- Open an issue on GitHub
- Reach out to the maintainers

Thank you for contributing to REanim!
