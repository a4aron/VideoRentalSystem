package ruleset;

import ui.videomanagement.VideoController;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class VideoRuleSet  implements RuleSet{

    @Override
    public void applyRules(Object ob) throws RuleException {
        VideoController vdoController = (VideoController) ob;
        applyNonEmptyRule(vdoController);
        validateType(vdoController);
    }
    public void applyNonEmptyRule(VideoController vdoController) throws RuleException {
        String moviename = vdoController.getTfMoviename();
        String status = vdoController.getTfStatus();
        String rating = vdoController.getTfRating();
        String genre = vdoController.getTfGenre();
        String director = vdoController.getTfDirector();
        String fee = vdoController.getTfRentalFee();
        String price = vdoController.getTfPrice();

        if (moviename.isEmpty())
            throw new RuleException("Movie name field cannot be blank!");
        if (rating.isEmpty())
            throw new RuleException("Rating field cannot be blank!");
        if (genre.isEmpty())
            throw new RuleException("Genre fee field cannot be blank!");
        if (director.isEmpty())
            throw new RuleException("Director name field cannot be blank!");
        if (fee.isEmpty())
            throw new RuleException("Rental fee cannot be blank!");
//        if (status.isEmpty())
//            throw new RuleException("Status field cannot be blank!");
//        if (price.isEmpty())
//            throw new RuleException("Price cannot be blank!");
    }

    public void validateType(VideoController vdoController) throws RuleException {
        String rentalFee = vdoController.getTfRentalFee();
        String price = vdoController.getTfPrice();
        String status = vdoController.getTfStatus();
        String rating = vdoController.getTfRating();

        List<String> validRating= Arrays.asList("1","2","3","4","5");
        if(!validRating.contains(rating)) throw new RuleException("Rating should be whole number between 1 - 5 !");
        try { Double.parseDouble(rentalFee);} catch (Exception e) {throw new RuleException("Rental fee field should have double value!");}
        try { Double.parseDouble(price);} catch (Exception e) {throw new RuleException("Price field should have double value!");}
        try { Boolean.parseBoolean(status);} catch (Exception e) {throw new RuleException("Status should be true/false!");}

    }
}
