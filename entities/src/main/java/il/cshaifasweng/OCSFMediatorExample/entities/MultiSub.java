package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
public class MultiSub {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    String MultiSubId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "PartialSubId")
    private List<PartialSub> cars;

    public List<PartialSub> getCars() {
        return this.cars;
    }
}

