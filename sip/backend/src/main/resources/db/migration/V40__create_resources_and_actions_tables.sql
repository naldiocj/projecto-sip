CREATE TABLE actions (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    is_deleted BOOLEAN DEFAULT FALSE
);

CREATE TABLE resources (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    role_id BIGINT,
    is_deleted BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_resources_role FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE resources_actions (
    resource_id BIGINT NOT NULL,
    action_id BIGINT NOT NULL,
    PRIMARY KEY (resource_id, action_id),
    CONSTRAINT fk_resources_actions_resource FOREIGN KEY (resource_id) REFERENCES resources (id),
    CONSTRAINT fk_resources_actions_action FOREIGN KEY (action_id) REFERENCES actions (id)
);
