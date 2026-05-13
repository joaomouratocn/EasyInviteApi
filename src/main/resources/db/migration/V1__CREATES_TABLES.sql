CREATE TABLE price(
    id VARCHAR(50) PRIMARY KEY,
    price DECIMAL(13,2) NOT NULL,
    enable BOOLEAN NOT NUll DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE themes (
    id VARCHAR(50) PRIMARY KEY,
    theme_name VARCHAR(100) NOT NULL,
    title VARCHAR(255),
    subtitle VARCHAR(255),
    modal_title VARCHAR(255),
    confirm_text TEXT,
    cover_url TEXT,
    bg_image_url TEXT,
    bg_prof_image_url TEXT,
    light_theme JSONB NOT NULL,
    dark_theme JSONB NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE invites (
     id SERIAL PRIMARY KEY,
     name VARCHAR(255) NOT NULL,
     slug VARCHAR(50) UNIQUE NOT NULL,
     age INTEGER,
     event_date TIMESTAMP NOT NULL,
     address TEXT,
     map_url TEXT,
     description TEXT[],
     confirmed_count INTEGER DEFAULT 0,
     show_age BOOLEAN DEFAULT TRUE,
     confirm_enable BOOLEAN DEFAULT TRUE,
     enable_timer BOOLEAN DEFAULT TRUE,
     dark_mode BOOLEAN DEFAULT TRUE,
     profile_url TEXT,
     status VARCHAR(10) CHECK (status IN ('WAP', 'ACT', 'EXP')),
     theme_id VARCHAR(50) REFERENCES themes(id),
     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
CREATE TABLE faqs (
    id SERIAL PRIMARY KEY,
    question TEXT NOT NULL,
    answer TEXT NOT NULL,
    display_order INTEGER DEFAULT 0
);