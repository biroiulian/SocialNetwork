package socialnetwork.socialnetwork.Domain;

import java.util.Objects;

public class FriendshipValidator {

    public FriendshipValidator() {
    }

    /**
     * Validate the Friendship so that a user can't friend itself
     * @param un1 username of first User
     * @param un2 username of second User
     * @throws InvalidFriendshipFormDataException
     */
    public void validateFriendshipFormData(String un1, String un2) throws InvalidFriendshipFormDataException{
        String errs="";
        if(Objects.equals(un1, un2)) errs=errs+"Userii trebuie sa fie diferiti";
        if(!errs.equals(""))throw new InvalidFriendshipFormDataException(errs);
    }
}
