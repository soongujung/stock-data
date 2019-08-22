from cliff.lister import Lister


class ListCommand(Lister):
    """
        Lister를 상속받는 클래스는
            1. get_parser(, prog_name)
            2. take_action(, parsed_args)
        를 오버라이딩 해야 한다. 이 둘은 abstract 메서드라고 한다.
    """

    def get_parser(self, prog_name):
        parser = super(ListCommand, self).get_parser(prog_name)
        parser.add_argument(
            '--group',
            metavar='<group-keyword>',
            help='Show commands'
        )
        return parser

    def take_action(self, parsed_args):
        return None
