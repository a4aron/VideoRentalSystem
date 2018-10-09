package ruleset;

import ui.videomanagement.VideoController;
import ui.customermanagement.CustomerController;
import ui.dashboard.DashboardController;
import ui.login.LoginController;
//import ui.rentalmanagement.RentalManagementController;
import ui.staffmanagement.StaffController;

import java.util.HashMap;

final public class RuleSetFactory {
	private RuleSetFactory() {
	}

	static HashMap<Class<? extends Object>, RuleSet> map = new HashMap<>();

	static {
		map.put(LoginController.class, new LoginRuleSet());
		map.put(VideoController.class, new VideoRuleSet());
		map.put(CustomerController.class, new CustomerRuleSet());
		map.put(StaffController.class, new StaffRuleSet());
		map.put(DashboardController.class, new DashboardRuleSet());

	}

	public static RuleSet getRuleSet(Object c) {
		Class<? extends Object> ob = c.getClass();
		if (!map.containsKey(ob)) {
			throw new IllegalArgumentException("Rule set undefined for this object type");
		}
		return map.get(ob);
	}
}
