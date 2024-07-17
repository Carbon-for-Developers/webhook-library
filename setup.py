from setuptools import setup, find_packages

setup(
    name='carbon_webhooks',
    version='0.1.0',
    packages=find_packages(),
    install_requires=[
        'requests',
    ],
    author='carbon',
    author_email='.email@example.com',
    description='A library to verify Carbon webhook events',
    long_description=open('README.md').read(),
    long_description_content_type='text/markdown',
    url='https://github.com/yourusername/carbon_webhooks',
    classifiers=[
        'Programming Language :: Python :: 3',
        'License :: OSI Approved :: MIT License',
        'Operating System :: OS Independent',
    ],
    python_requires='>=3.6',
)