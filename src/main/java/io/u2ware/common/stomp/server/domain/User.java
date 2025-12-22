package io.u2ware.common.stomp.server.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.u2ware.common.stomp.server.domain.auditing.AuditedEntity;
import io.u2ware.common.stomp.server.domain.properties.AttributesSet;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "examples_users")
@Data @EqualsAndHashCode(callSuper = true)
public class User extends AuditedEntity {

    @Id
    private String userId;

    private AttributesSet roles = new AttributesSet();

    @Transient
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String searchKeyword;
}