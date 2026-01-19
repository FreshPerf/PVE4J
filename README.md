<p align="center">
  <img width="1000" height="250" alt="PVE4J logo" src="https://github.com/user-attachments/assets/86626cd7-1284-4ef1-b5bb-fd192894ea12"
    />
</p>
<p align="center">
  <a href="https://www.gnu.org/licenses/gpl-3.0"><img src="https://img.shields.io/badge/License-GPLv3-blue.svg" alt="License: GPL v3"></a>
  <a href="https://central.sonatype.com/artifact/fr.freshperf/PVE4J"><img src="https://img.shields.io/badge/release-0.1.1-green" alt="Maven Central"></a>
  <a href="https://github.com/FreshPerf/PVE4J/issues"><img src="https://img.shields.io/github/issues/FreshPerf/PVE4J" alt="GitHub Issues"></a>
  <a href="https://github.com/FreshPerf/PVE4J/stargazers"><img src="https://img.shields.io/github/stars/FreshPerf/PVE4J" alt="GitHub Stars"></a>
  <a href="https://freshperf.fr"><img src="https://img.shields.io/badge/FreshPerf-v1.5-blue" alt="FreshPerf"></a>
</p>

<p align="center">
  <em>Proxmox® is a registered trademark of Proxmox Server Solutions GmbH. PVE4J is not affiliated with, endorsed by, or sponsored by Proxmox Server Solutions GmbH.</em>
</p>

<p align="center">
  A modern, fluent, and comprehensive Java library for interacting with the Proxmox VE API, created and maintained by <a target="_blank" href="https://freshperf.fr">FreshPerf</a>.
</p>

---

## Overview

PVE4J is a Java library that provides an object-oriented wrapper around the Proxmox Virtual Environment REST API. It enables programmatic management of Proxmox infrastructure with a clean, intuitive, and type-safe interface.

**Note:** This library is currently in active development. While core functionality is implemented and stable, some API endpoints may be missing. Contributions and feedback are welcome.


## Features

- **Comprehensive API Coverage** - Manage cluster resources, nodes, QEMU virtual machines, LXC containers, storage, and access controls (users, groups, roles, ACLs)
- **Fluent API Design** - Clean, chainable request system for intuitive interaction with the Proxmox API
- **Flexible Authentication** - Support for both API tokens and username/password authentication
- **Asynchronous Task Handling** - Built-in support for long-running operations with `waitForCompletion()` and `onCompletion()` callback methods
- **Automatic Retry Support** - Configurable retry logic with customizable delays for transient failures
- **Robust Error Handling** - Clear and informative `ProxmoxAPIError` exceptions for easier debugging
- **Type-Safe Entities** - Maps Proxmox API responses to strongly-typed Java objects
- **Configurable Security** - Easily manage SSL/TLS certificate and hostname verification for different environments
- **Detailed VM Management** - Create, clone, delete, resize disks, and update configurations for QEMU VMs with type-safe builder options
- **Firewall Management** - Configure firewall options and manage IP Sets for QEMU VMs
- **Storage Management** - Query storage status, list content, and retrieve statistics

## Current Implementation Status

### Fully Implemented

- **Cluster Management**
  - Cluster status and resources
  - Next available VMID retrieval
  - Resource filtering by type

- **Node Management**
  - List all nodes
  - Node status and version information
  - Node-specific operations

- **QEMU VM Management**
  - List, create, clone, and delete VMs
  - VM lifecycle operations (start, stop, shutdown, reboot, reset, suspend, resume)
  - VM configuration management (get/update)
  - Disk resizing
  - VNC proxy access
  - Firewall configuration
  - IP Set management

- **LXC Container Management**
  - List and query containers
  - Container lifecycle operations (start, stop, shutdown, reboot, suspend, resume)
  - Container configuration retrieval
  - Container deletion

- **Storage Management**
  - List storage pools
  - Storage status and content
  - RRD statistics

- **Access Control**
  - User management
  - Group management
  - Role management
  - ACL (Access Control List) queries
  - Authentication domains/realms
  - Ticket-based authentication

### Planned/In Progress

- Additional network configuration options
- Backup and restore operations
- Snapshot management
- HA (High Availability) resource management
- Pool management
- Additional firewall rules management

## Installation

### Maven Central

#### Gradle

Add the dependency to your `build.gradle`:

```groovy
dependencies {
    implementation 'fr.freshperf:PVE4J:0.1.1'
}
```

#### Maven

Add the dependency:

```xml
<dependency>
    <groupId>fr.freshperf</groupId>
    <artifactId>PVE4J</artifactId>
    <version>0.1.1</version>
</dependency>
```

## Quick Start

### Creating a Client

First, create a `Proxmox` client instance. You can use either an API token (recommended) or username/password authentication.

#### Using API Token (Recommended)

```java
import fr.freshperf.PVE4J.Proxmox;
import fr.freshperf.PVE4J.SecurityConfig;

// For production environments with valid SSL certificates
Proxmox proxmox = Proxmox.create("pve.example.com", 8006, "your-api-token");

// For development/testing with self-signed certificates
Proxmox proxmoxDev = Proxmox.create("192.168.1.10", 8006, "your-api-token", SecurityConfig.insecure());
```

#### Using Username/Password

```java
import fr.freshperf.PVE4J.Proxmox;
import fr.freshperf.PVE4J.SecurityConfig;
import fr.freshperf.PVE4J.throwable.ProxmoxAPIError;

try {
    // With default PAM realm
    Proxmox proxmox = Proxmox.createWithPassword("pve.example.com", 8006, "root", "password");
    
    // With custom realm and security config
    Proxmox proxmox = Proxmox.createWithPassword("192.168.1.10", 8006, "admin", "password", "pve", SecurityConfig.insecure());
} catch (ProxmoxAPIError | InterruptedException e) {
    e.printStackTrace();
}
```

### Basic Operations

**Get Proxmox Version**

```java
import fr.freshperf.PVE4J.entities.PveVersion;
import fr.freshperf.PVE4J.throwable.ProxmoxAPIError;

try {
    PveVersion version = proxmox.getVersion().execute();
    System.out.println("Proxmox Version: " + version.getVersion());
} catch (ProxmoxAPIError | InterruptedException e) {
    e.printStackTrace();
}
```

**List All QEMU VMs on a Specific Node**

```java
import fr.freshperf.PVE4J.entities.nodes.node.qemu.PveQemuIndex;
import java.util.List;

try {
    List<PveQemuIndex> vms = proxmox.getNodes()
            .get("pve-node-01")
            .getQemu()
            .getIndex()
            .execute();
            
    for (PveQemuIndex vm : vms) {
        System.out.printf("VMID: %d, Name: %s, Status: %s%n",
                vm.getVmid(), vm.getName(), vm.getStatus());
    }
} catch (ProxmoxAPIError | InterruptedException e) {
    e.printStackTrace();
}
```

### Managing VMs and Tasks

PVE4J simplifies handling long-running tasks like starting or cloning a VM.

**Start a VM and Wait for Completion**

```java
import fr.freshperf.PVE4J.entities.PveTask;

try {
    PveTask startTask = proxmox.getNodes()
            .get("pve-node-01")
            .getQemu()
            .get(101)
            .start()
            .waitForCompletion(proxmox)
            .execute();
            
    System.out.println("VM 101 started successfully. Task: " + startTask.getUpid());
    
} catch (ProxmoxAPIError | InterruptedException e) {
    System.err.println("Failed to start VM 101: " + e.getMessage());
}
```

**Clone a VM with an Asynchronous Callback**

```java
import fr.freshperf.PVE4J.entities.nodes.node.qemu.PveQemuCloneOptions;

try {
    PveQemuCloneOptions cloneOptions = PveQemuCloneOptions.builder()
            .name("clone-of-template")
            .description("A new VM cloned via PVE4J")
            .build();
            
    proxmox.getNodes()
            .get("pve-node-01")
            .getQemu()
            .get(9000)
            .cloneVm(201, cloneOptions)
            .onCompletion(status -> {
                if (status.isSuccessful()) {
                    System.out.println("VM 201 cloned successfully!");
                } else {
                    System.err.println("Cloning failed: " + status.getExitstatus());
                }
            }, proxmox)
            .execute();
            
    System.out.println("Clone task submitted.");
} catch (ProxmoxAPIError | InterruptedException e) {
    e.printStackTrace();
}
```

## API Overview

The library's functionality is structured hierarchically, starting from the main `Proxmox` object:

- `proxmox.getVersion()` - Get the API version information
- `proxmox.getTaskStatus(task)` - Check the status of a running task
- `proxmox.getCluster()` - Access cluster-wide information and resources
  - `.getStatus()` - Get the status of all cluster members
  - `.getResources()` - List all resources in the cluster (VMs, containers, storage, etc.)
  - `.getNextId()` - Get the next available VMID
- `proxmox.getNodes()` - List all nodes in the cluster
  - `.get("node-name")` - Access a specific node by its name
    - `.getQemu()` - Manage QEMU VMs on the node
      - `.get(vmid)` - Access a specific VM
        - `.start()`, `.stop()`, `.shutdown()`, `.reboot()`, `.reset()`
        - `.suspend()`, `.resume()`
        - `.cloneVm()`, `.resize()`, `.delete()`
        - `.updateConfig()`, `.getConfig()`, `.getStatus()`
        - `.firewall()` - Manage VM firewall settings
    - `.getLxc()` - Manage LXC containers on the node
    - `.getStorage()` - Manage storage pools on the node
- `proxmox.getAccess()` - Manage access control
  - `.getUsers()` - List and manage users
  - `.getGroups()` - List and manage groups
  - `.getRoles()` - List and manage roles
  - `.getAcl()` - Query access control lists
  - `.getDomains()` - List authentication domains

## Security Configuration

The `SecurityConfig` class allows you to control SSL/TLS verification:

- **`SecurityConfig.secure()` (Default)** - Enforces both SSL certificate validation and hostname verification. Use this for production environments with properly configured certificates.

```java
Proxmox proxmox = Proxmox.create("pve.example.com", 8006, "token");
```

- **`SecurityConfig.insecure()`** - Disables both SSL certificate and hostname verification. This is useful for development or testing environments where Proxmox is using a self-signed certificate.

```java
Proxmox proxmox = Proxmox.create("192.168.1.10", 8006, "token", SecurityConfig.insecure());
```

- **Custom Configuration** - Use the builder for fine-grained control.

```java
SecurityConfig customConfig = SecurityConfig.builder()
        .disableSslVerification()
        .enableHostnameVerification()
        .build();

// Or disable all security checks at once
SecurityConfig allDisabled = SecurityConfig.builder()
        .disableAll()
        .build();
```

## Documentation

For detailed documentation, please refer to the [Wiki](wiki/Home):

- [Getting Started](wiki/Getting-Started)
- [Authentication](wiki/Authentication)
- [Client Configuration](wiki/Client-Configuration)
- [Request API](wiki/Request-API)
- [VM Management](wiki/VM-Management)
- [Container Management](wiki/Container-Management)
- [Cluster Operations](wiki/Cluster-Operations)
- [Storage Management](wiki/Storage-Management)
- [Access Control](wiki/Access-Control)
- [Task Handling](wiki/Task-Handling)
- [Error Handling](wiki/Error-Handling)

## Contributing

Contributions are welcome and appreciated! Here's how you can help:

### Reporting Issues

If you encounter a bug or have a feature request:

1. Check the [issue tracker](https://github.com/FreshPerf/PVE4J/issues) to see if it's already reported
2. If not, create a new issue with a clear description
3. Include code samples, error messages, and your environment details

### Contributing Code

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Make your changes following the existing code style
4. Write tests for your changes
5. Commit your changes (`git commit -m 'Add some amazing feature'`)
6. Push to the branch (`git push origin feature/amazing-feature`)
7. Open a Pull Request

### Development Guidelines

- Follow Java best practices and naming conventions
- Maintain the fluent API design pattern
- Add JavaDoc comments for public APIs
- Write unit tests for new functionality
- Ensure all tests pass before submitting a PR

### Areas for Contribution

We particularly welcome contributions in these areas:

- Missing API endpoint implementations
- Additional builder options for existing operations
- Documentation improvements
- Example code and tutorials
- Bug fixes and performance improvements

## License

This project is licensed under the GNU General Public License v3.0. See the [LICENSE](LICENSE) file for details.

## Acknowledgments

- The Proxmox VE team for their excellent virtualization platform and API documentation
- All contributors who help improve this library

## Support

- **Documentation:** [Wiki](wiki/Home)
- **Issues:** [GitHub Issues](https://github.com/FreshPerf/PVE4J/issues)
- **AI Assistant:** [Ask DeepWiki](https://deepwiki.com/FreshPerf/PVE4J)

---

<p align="center">Maintainted with with 🩵 by the <a href="https://freshperf.fr" target="_blank">FreshPerf.fr</a> Team.</p>

