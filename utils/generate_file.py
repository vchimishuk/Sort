import string
import random
from datetime import datetime


MAX_INT = 2 ** 31 - 1


def generate_email():
    l = random.randint(10, 300)

    return ''.join(random.choice(string.ascii_lowercase) for x in range(l))


def generate_time():
    t = random.randint(0, MAX_INT)

    return datetime.fromtimestamp(t).strftime("%Y-%m-%dT%H:%M:%S")

def generate_id():
    return random.randint(0, MAX_INT)


def generate_record():
    return '{0} {1} {2}'.format(generate_email(), generate_time(), generate_id())


for i in range(100000):
    print(generate_record())
