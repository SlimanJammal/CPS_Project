package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Subscription" )
public class MultiSub extends Subscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String MultiSubId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "PartialSubId")
    private List<PartialSub> cars;
}
