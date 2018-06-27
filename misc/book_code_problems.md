### Issues with the code from the book

#### page 90
  - Presented class **ApplicationRunner** references constant **XMPP_HOSTNAME** that is not defined in the code. But from the previous book chapters it is said it should be set to 'localhost'
#### page 91
  - Presented class **AuctionSniperDriver** references constant **Main.MAIN_WINDOW_NAME** that is not defined  
  (from the error message on page 97 it appears that the value must be 'Auction Sniper Main')
  - Presented class **AuctionSniperDriver** references constant **Main.SNIPER_STATUS_NAME** that are not defined
  (from the error message on page 97 it appears that the value must be 'sniper status')
#### page 96  
  - On the top of page 96 there is an output from failing test. But at this point in book the code would not even compile because the definition of **Main** class is missing.
    First **Main** class code is presented further on page 97.
  - Also, added code line (in bold) **startUserInterface()** is missing semicolon.
  - In the **Main** class there is a reference to **MainWindow** class that was not shown in the book until page 97
  

### Compilation problems
(Libraries taken from https://github.com/sf105/goos-code )
WindowLicker version provided with the original goos code (provided as lib in directory lib/develop) does not support keyboard layouts other than US/GB/MAC.GB
(look at: windowlicker-core-DEV.jar!\com\objogate\wl\keyboard)

On my machine after execution of first e2e test I got:
```
java.lang.IllegalArgumentException: keyboard layout PL not available.

	at com.objogate.wl.keyboard.KeyboardLayout.getKeyboardLayout(KeyboardLayout.java:82)
	at com.objogate.wl.keyboard.KeyboardLayout.getDefaultKeyboardLayout(KeyboardLayout.java:75)
	at com.objogate.wl.robot.RoboticAutomaton.<init>(RoboticAutomaton.java:27)
	at com.objogate.wl.swing.gesture.GesturePerformer.<init>(GesturePerformer.java:13)
	at end_2_end.AuctionSniperDriver.<init>(AuctionSniperDriver.java:13)
	at end_2_end.ApplicationRunner.startBiddingIn(ApplicationRunner.java:26)
	at end_2_end.AuctionSniperEndToEndTest.sniperJoinsAuctionUntilAuctionCloses(AuctionSniperEndToEndTest.java:13)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:44)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:15)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:41)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:20)
	at org.junit.internal.runners.statements.RunBefores.evaluate(RunBefores.java:28)
	at org.junit.internal.runners.statements.RunAfters.evaluate(RunAfters.java:31)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:70)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:44)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:183)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:41)
	at org.junit.runners.ParentRunner$1.evaluate(ParentRunner.java:176)
	at org.junit.internal.runners.statements.RunBefores.evaluate(RunBefores.java:28)
	at org.junit.internal.runners.statements.RunAfters.evaluate(RunAfters.java:31)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:223)
	at org.junit.runner.JUnitCore.run(JUnitCore.java:159)
	at com.intellij.junit4.JUnit4IdeaTestRunner.startRunnerWithArgs(JUnit4IdeaTestRunner.java:68)
	at com.intellij.rt.execution.junit.IdeaTestRunner$Repeater.startRunnerWithArgs(IdeaTestRunner.java:47)
	at com.intellij.rt.execution.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:242)
	at com.intellij.rt.execution.junit.JUnitStarter.main(JUnitStarter.java:70)
```

to fix that I've added setup code to e2e test inside @Before section
```
 @Before
    public void forceGBKeyboardLayoutForSmackLibraryIfRealKeyboardLayoutIsUnsupported(){
        final String country = InputContext.getInstance().getLocale().getCountry();
        if(!isKeyboardLayoutSupportedByWindowLicker(country)){
            System.setProperty("com.objogate.wl.keyboard", "GB");
        }
    }

    private boolean isKeyboardLayoutSupportedByWindowLicker(String country) {
        return country.equals("GB") || country.equals("US") || country.equals("MAC.GB");
    }
```