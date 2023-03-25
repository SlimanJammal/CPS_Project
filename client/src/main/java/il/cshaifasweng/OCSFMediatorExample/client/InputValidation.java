package il.cshaifasweng.OCSFMediatorExample.client;

public class InputValidation
{
    //Input a string of only digits returns true else returns false
    public boolean CustomerIDValidation(String input) {
        if (input == "") {
            return false;
        }
        try {
            int d = Integer.parseInt(input);
        }

        catch (NumberFormatException nfe) {
            return false;
        }
        catch (Exception e)
        {
            System.out.println("Not Legal");
        }
        return true;
    }

    //Input a string of only digits returns true else returns false
    public boolean CarIDValidation(String input) {
        if (input == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(input);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;    }

    //Input a string of format HH:MM returns true if it's time else returns false
    public boolean TimeValidation(String input) {
        if (input == null) {
            return false;
        }
        String regex = "^([0-1]\\d|2[0-3]):([0-5]\\d)$";
        return input.matches(regex);
    }

    //Input a string of format DD.MM.YYYY returns true if it's time else returns false
    public boolean DateValidation(String input) {
        if (input == null) {
            return false;
        }
        String regex = "^([0-2][1-9]|[1-3]0|31)\\.(0[1-9]|1[0-2])\\.\\d{4}$";
        return input.matches(regex);
    }

    //Input a string of format text@text.text as an email representation returns true else returns false
    public boolean EmailValidation(String input) {
        if (input == null) {
            return false;
        }
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        return input.matches(regex);
    }

    public boolean ValidateAccount(String input)
    {
        return true;
    }


}
