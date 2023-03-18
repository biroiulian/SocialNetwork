package socialnetwork.socialnetwork.Domain;

public class UserValidator {

    public UserValidator() {
    }

    /**
     * Validates the User to be older than 15 years, the username and password to have more than 6 and less than characters
     * @param un username of User
     * @param pass password of User
     * @param age age of User
     * @throws InvalidUserFormDataException
     */
    public void validateUserFormData(String un, String pass, int age) throws InvalidUserFormDataException{
        String errs="";
        if(un.length()<6 || un.length()>12)errs=errs+"Username trebuie sa aiba 6-12 caractere si fara spatiu\n";
        if(pass.length()<6 || pass.length()>12)errs=errs+"Parola trebuie sa aiba 6-12 caractere\n";
        if(age<15)errs=errs+"Trebuie sa ai peste 15 ani ca sa fii user\n";
        if(!errs.equals(""))throw new InvalidUserFormDataException(errs);
    }
}
