package il.cshaifasweng.OCSFMediatorExample.entities;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table
public class complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String ComplaintId;
    @NotNull
    boolean Entered;



}
