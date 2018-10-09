package ruleset;

import ui.dashboard.DashboardController;

import java.time.LocalDate;

public class DashboardRuleSet implements RuleSet{
    @Override
    public void applyRules(Object ob) throws RuleException {
        DashboardController dashboardController = (DashboardController) ob;
        idNotEmpty(dashboardController);
        videoAvailable(dashboardController);
        dateValid(dashboardController);
    }

    private void videoAvailable(DashboardController dashboardController) throws RuleException{
        if (dashboardController.getStatus().toLowerCase()=="not available") throw new RuleException("Movie is already rented");
    }

    private void idNotEmpty(DashboardController dashboardController) throws RuleException{
        if (dashboardController.getCustomerId()==null) throw new RuleException("Select a customer");
        if (dashboardController.getVideoId()== -1) throw new RuleException("Select a Movie");
        if (dashboardController.getRentDate()==null) throw new RuleException("Select rent date");
        if (dashboardController.getExpectedDate()==null) throw new RuleException("Select expected return date");
    }

    private void dateValid(DashboardController dashboardController) throws RuleException{
        if (dashboardController.getRentDate().isAfter(dashboardController.getExpectedDate()))
            throw new RuleException("Rent date should be before return date");
//        if (dashboardController.getRentDate().isBefore(LocalDate.now()))
//            throw new RuleException("Rent date should not be before today");
    }
}
