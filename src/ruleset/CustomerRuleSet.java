package ruleset;

import ui.customermanagement.CustomerController;

public class CustomerRuleSet implements RuleSet {
    @Override
    public void applyRules(Object ob) throws RuleException {
        CustomerController customerController = (CustomerController) ob;
        applyNonEmptyRule(customerController);
//        validatePhone(customerController);
    }

    public void applyNonEmptyRule(CustomerController customerController) throws RuleException {
        String fName = customerController.getFirstName();
        String lName = customerController.getLastName();
        String phone = customerController.getPhone();
        String address = customerController.getAddress();

        if (fName.isEmpty())
            throw new RuleException("First name field cannot be blank!");
        if (lName.isEmpty())
            throw new RuleException("Last name field cannot be blank!");
        if (phone.isEmpty())
            throw new RuleException("Phone field cannot be blank!");
        if (address.isEmpty())
            throw new RuleException("Address field cannot be blank!");
    }

    public void validatePhone(CustomerController customerController) throws RuleException {
        try { Long.parseLong(customerController.getPhone());} catch (Exception e) {throw new RuleException("Phone field should have long value!");}
    }
}
