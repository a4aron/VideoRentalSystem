package ruleset;

import ui.login.LoginController;

public class LoginRuleSet implements RuleSet {
	LoginRuleSet() {
	}

	@Override
	public void applyRules(Object ob) throws RuleException {
		LoginController controller = (LoginController) ob;
		applyNonEmptyRule(controller);
	}

	private void applyNonEmptyRule(LoginController controller) throws RuleException {
		String username = controller.getUsername();
		String password = controller.getPassword();

		if (username.isEmpty() && password.isEmpty())
			throw new RuleException("Username and Password field cannot be blank!");
		if (username.isEmpty())
			throw new RuleException("Username can't be blank!");
		if (password.isEmpty())
			throw new RuleException("Password can't be blank!");
	}
}
