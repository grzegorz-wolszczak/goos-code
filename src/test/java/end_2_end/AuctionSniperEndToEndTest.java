package end_2_end;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.awt.im.InputContext;

public class AuctionSniperEndToEndTest {
    private final FakeAuctionServer auction = new FakeAuctionServer("item-54321");
    private final ApplicationRunner application = new ApplicationRunner();

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

    @Test
    public void sniperJoinsAuctionUntilAuctionCloses() throws Exception {
        auction.startSellingItem();                 // Step 1
        application.startBiddingIn(auction);        // Step 2
        auction.hasReceivedJoinRequestFromSniper(); // Step 3
        auction.announceClosed();                   // Step 4
        application.showsSniperHasLostAuction();    // Step 5
    }
    // Additional cleanup
    @After
    public void stopAuction() {
        auction.stop();
    }
    @After public void stopApplication() {
        application.stop();
    }
}
