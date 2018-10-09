package ruleset;

//import ui.customermanagement.CustomerController;
import ui.staffmanagement.StaffController;

public class StaffRuleSet implements RuleSet {
    @Override
    public void applyRules(Object ob) throws RuleException {
        StaffController staffController = (StaffController) ob;
        applyNonEmptyRule(staffController);
        validateType(staffController);
    }

    public void applyNonEmptyRule(StaffController staffController) throws RuleException {
        String uName = staffController.getFirstName();
        String fName = staffController.getFirstName();
        String lName = staffController.getLastName();
        String phone = staffController.getPhone();
        String address = staffController.getAddress();
        String salary = staffController.getSalary();
        String role = staffController.getRole();

        if (uName.isEmpty())
            throw new RuleException("Username field cannot be blank!");
        if (fName.isEmpty())
            throw new RuleException("First name field cannot be blank!");
        if (lName.isEmpty())
            throw new RuleException("Last name field cannot be blank!");
        if (phone.isEmpty())
            throw new RuleException("Phone field cannot be blank!");
        if (address.isEmpty())
            throw new RuleException("Address field cannot be blank!");
        if (salary.isEmpty())
            throw new RuleException("Address field cannot be blank!");
        if (role.isEmpty())
            throw new RuleException("Address field cannot be blank!");
    }

    public void validateType(StaffController staffController) throws RuleException {
//        try { Long.parseLong(staffController.getPhone());} catch (Exception e) {throw new RuleException("Phone field should have long value!");}
        try { Double.parseDouble(staffController.getSalary());} catch (Exception e) {throw new RuleException("Salary field should have double value!");}
        try { Integer.parseInt(staffController.getRole());} catch (Exception e) {throw new RuleException("Role field should have integer value!");}
    }

}
