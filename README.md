# Smart Assistant for Android (Kotlin + C++)

This repository now contains a starter Android app for a **voice-first smart assistant** with a C++ command engine.

## What is included

- Android foreground service (`VoiceAssistantService`) for always-on assistant behavior.
- Accessibility service (`DeviceControlAccessibilityService`) to run allowed global actions such as:
  - Open notifications
  - Open quick settings
  - Go home
- Boot receiver (`BootReceiver`) so the assistant can restart after reboot and continue working after locked boot.
- Native C++ command parser (`assistant_core.cpp`) exposed through JNI.
- Basic control UI (`MainActivity`) to start/stop assistant and open accessibility settings.

## Important notes on "all device control" and lock-screen behavior

Android restricts powerful device control for security/privacy reasons. This project follows platform-safe patterns:

- Full device control is **not** possible without privileged/system app status.
- Background/lock-screen features require explicit user permission and OEM compatibility.
- Accessibility actions work only after users manually enable the service in system settings.

## Build

1. Open in Android Studio (Hedgehog+ recommended).
2. Let Gradle sync.
3. Build and run on a real device (recommended for voice/accessibility features).
4. Enable the accessibility service in **Settings → Accessibility**.

## Next steps you can add

- Replace mock command input with SpeechRecognizer or offline ASR (e.g., Vosk/Whisper.cpp).
- Add wake-word detection.
- Add secure intents for common app/device workflows.
- Add on-device LLM inference for richer intent extraction.
