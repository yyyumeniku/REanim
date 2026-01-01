# REanim - Vanilla-Style Animation Library

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)
[![Minecraft](https://img.shields.io/badge/Minecraft-1.12.2-green.svg)](https://minecraft.net)
[![Forge](https://img.shields.io/badge/Forge-14.23.5.2847+-orange.svg)](https://files.minecraftforge.net)

A lightweight animation library for Minecraft 1.12.2 Forge that provides vanilla-looking player and entity animations using Mixin hooks.

## 📋 Table of Contents

- [Features](#features)
- [Quick Start](#quick-start)
- [API Overview](#api-overview)
- [Coordinate System](#coordinate-system)
- [Dependencies](#dependencies)
- [Contributing](#contributing)
- [License](#license)

## Features

- **Custom Poses** - Define static poses for any body part
- **Procedural Animations** - Create smooth animations using math functions (sin/cos)
- **Static Pose Animations** - Hold poses with optional trembling effects
- **Smooth Transitions** - Automatic interpolation between animations
- **Multiplayer Support** - Animation state syncs between server and clients
- **Priority System** - Layer multiple animations with priority-based overrides
- **Clean API** - Simple fluent builder for creating poses

## Quick Start

### 1. Add REanim as a dependency

```gradle
dependencies {
    implementation files('libs/reanim-1.0.0.jar')
}
```

### 2. Register your animations

```java
@Mod.EventHandler
public void preInit(FMLPreInitializationEvent event) {
    // Register a climbing animation
    AnimationRegistry.register(new ProceduralAnimation(
        new ResourceLocation("mymod", "climb"),
        20, // 1 second duration
        true, // looping
        (progress, partialTicks) -> {
            float armSwing = MathHelper.sin(progress * (float)Math.PI * 2) * 0.9f;
            return PoseBuilder.create()
                .rightArm(-2.2f + armSwing, -0.2f, 0.35f)
                .leftArm(-2.2f - armSwing, 0.2f, -0.35f)
                .body(0.25f, 0, 0)
                .build();
        }
    ));
    
    // Register a static pose for clinging
    AnimationRegistry.register(new StaticPoseAnimation(
        new ResourceLocation("mymod", "cling"),
        PoseBuilder.create()
            .rightArm(-2.5f, -0.1f, 0.15f)
            .leftArm(-2.5f, 0.1f, -0.15f)
            .body(0.15f, 0, 0)
            .build(),
        0.02f // subtle trembling
    ));
}
```

### 3. Play animations

```java
// Start an animation
AnimationAPI.play(player, new ResourceLocation("mymod", "climb"));

// Check if playing
if (AnimationAPI.isPlaying(player)) {
    // ...
}

// Stop animation
AnimationAPI.stop(player);
```

## API Overview

### PoseBuilder
Fluent builder for creating poses:
```java
IPose pose = PoseBuilder.create()
    .head(0, 0, 0)           // X, Y, Z rotation in radians
    .rightArm(-2.2f, 0, 0.3f)
    .leftArm(-2.2f, 0, -0.3f)
    .rightLeg(0.2f, 0, 0)
    .leftLeg(0.2f, 0, 0)
    .body(0.2f, 0, 0)
    .build();
```

### Animation Types

- **StaticPoseAnimation** - Holds a single pose, optionally with trembling
- **ProceduralAnimation** - Generates poses using a function (most flexible)

### AnimationAPI
Main entry point:
- `play(entity, animationId)` - Start an animation
- `stop(entity)` - Stop current animation
- `isPlaying(entity)` - Check if animation is active
- `getCurrentPose(entity, partialTicks)` - Get the current pose

## Coordinate System

All angles are in **radians**:
- Use `(float)Math.toRadians(degrees)` to convert
- Or use constants: `(float)Math.PI / 2` = 90°

Body part rotations:
- **X** = Pitch (forward/backward tilt)
- **Y** = Yaw (left/right rotation)
- **Z** = Roll (sideways tilt)

Positive values:
- **+X** = tilts forward
- **+Y** = rotates left
- **+Z** = rolls right

## Dependencies

- Minecraft 1.12.2
- Forge 14.23.5.2847+
- MixinBooter 10.2+

## Contributing

We welcome contributions! Please see our [Contributing Guidelines](CONTRIBUTING.md) for details on how to get started.

### Reporting Issues

Found a bug or have a feature request? Please check our [issue tracker](../../issues) and create a new issue if it hasn't been reported yet.

## License

MIT License - Use freely in your mods! See [LICENSE](LICENSE) for details.

## Author

YYYumeniku

---

**Star this repository if you find it useful! ⭐**
