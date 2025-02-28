package com.akimi.issue_tracking.domain;

import org.junit.jupiter.api.Test;

/**
 * Utilizes many of the services meant to be invoked by the controllers of the 
 * app.
 */
public class HexagonalAppTest extends MainScenariosFineGrainedTest {
    
    @Test
    public void Minga() {
	user.purchase(app, support);
    }
}
