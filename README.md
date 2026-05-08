# Filter Module Template

A clean template for an Xposed/LSPosed module.

## Features
- Modular hook management
- Context initialization in target application
- Persistent configuration using SharedPreferences
- Logging utility

## Project Structure
- `MainHook`: Entry point for Xposed (`com.bushi.gaodefilter.MainHook`).
- `MainActivity`: Information page for the module.
- `utils/`: Log and Preference helpers.

## How to use
1. Modify `MainHook` to add your specific hooks.
2. Update the `xposedscope` in `res/values/arrays.xml` to include your target app.
3. Build and install.
