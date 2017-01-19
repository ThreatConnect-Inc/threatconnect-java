import json
from setuptools import setup, find_packages

with open('install.json', 'r') as fh:
    version = json.load(fh)['programVersion']

if not version:
    raise RuntimeError('Cannot find version information')

setup(
    author='ThreatConnect Inc',
    author_email='support@threatconnect.com',
    description='ThreatConnect Python Test Exchange App.',
    install_requires=[
        'tcex==0.0.7'
    ],
    license='Apache License, Version 2',
    name='tc_-_python_test',
    packages=find_packages(),
    url='https://bitbucket.org/threatconnectinc/tc_-_python_sdk_test',
    version=version
)
