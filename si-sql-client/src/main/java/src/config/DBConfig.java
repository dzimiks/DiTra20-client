package src.config;

import java.sql.Connection;

public abstract class DBConfig {
	public abstract Connection getDbConnection();
	public abstract void buildConnection();
}
