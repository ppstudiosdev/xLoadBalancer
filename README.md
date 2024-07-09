# xLoadBalancer Plugin

The **xLoadBalancer** plugin for Velocity optimizes player distribution across various servers in your network based on player count.

## Features

- Dynamically distribute players across lobby and spawn servers.
- Monitor server availability and player counts.
- Logger integration for detailed logging.

## Installation

1. Download the latest version of the plugin from the [Releases page](link_to_releases).
2. Place the downloaded JAR file into the `plugins` folder of your Velocity server.

## Configuration

The plugin does not require specific configuration. It uses default server names and maximum player counts for lobby and spawn servers.

## Usage

1. Start your Velocity server.
2. The plugin automatically monitors player logins and redirects them to available lobby and spawn servers.

## Commands and Events

The plugin does not introduce specific commands but responds to events such as player logins to evenly distribute load across available servers.

## Dependencies

- Velocity API

## Contributing

Contributions are welcome! If you find bugs or have suggestions, please create an issue or pull request on GitHub.

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
