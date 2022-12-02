# Testing Guidelines
As per https://developer.android.com/training/testing/ there are 4 subjects of testing:
 - Functional (Does it do what its supposed to),
 - Performance (Does it work in a reasonable amount of time)
 - Accessibility (Does it work if certain accessibility settings are on)
 - Compatibility (Does it work on a variety of device sizes and API levels)
We probably don't need to test Performance but we should write tests for the other 3.

The Hierarchies of testing are:
 - Unit Tests (Test a whole class by: JUnit to check simple methods; Mockito tests for methods that need database or android API)
 - Integration Tests (Tests to see if multiple classes work together)
 - End-to-End Tests (Espresso tests to see if a whole user story works)
 - App Testing (Whole app test, which we can do manually)

The src folder of this project has 2 testing folders:
 - androidTest for end-to-end & some Integration tests that need access to the android API. The espresso tests will go here.
 - test: for local unit tests that can be run without the emulator.











































































