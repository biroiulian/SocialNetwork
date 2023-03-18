package socialnetwork.socialnetwork.Domain;

import java.time.LocalDateTime;

public class AuxFriendship {
    public Long id1;
    public Long id2;
    public LocalDateTime dt;
    public String status;

    public AuxFriendship(Long id1, Long id2, LocalDateTime dt, String st) {
        this.id1 = id1;
        this.id2 = id2;
        this.dt = dt;
        this.status=st;
    }
}