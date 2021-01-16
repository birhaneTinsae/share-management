package com.enat.sharemanagement.share;

import com.enat.sharemanagement.shareholder.Shareholder;
import com.enat.sharemanagement.utils.Auditable;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity(name="shares")
public class Share extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne()
    private Shareholder shareholder;
}
