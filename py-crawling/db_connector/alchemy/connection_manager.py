from sqlalchemy import create_engine
from credentials.connection.database import postgresql_dev


class ConnectionManager:
    postgresql_connection_url = "postgresql://{}:{}@localhost:5432/{}"
    alchemy_conn = None

    def __init__(self, param):
        print('init')
        super().__init__()

    def __new__(cls, *args, **kwargs):
        print('new')
        return super().__new__(cls)

    @classmethod
    def __get_instance(cls):
        return cls.__instance

    @classmethod
    def instance(cls):
        if not cls.__instance:
            cls.__instance = ConnectionManager()
            cls.instance = cls.__get_instance()

        if not cls.alchemy_conn:
            cls.alchemy_conn = cls.create_connection(cls, postgresql_dev)

        return cls.__instance

    @classmethod
    def create_connection(cls, connection_info):
        if not cls.alchemy_conn:
            cls.alchemy_conn = create_engine(
                cls.postgresql_connection_url.format(
                    connection_info['user'],
                    connection_info['password'],
                    connection_info['dbname']
                )
            )

        return cls.alchemy_conn

    @classmethod
    def get_connection(cls):
        return cls.alchemy_conn

