#!/bin/bash -eu

# ----------------------------------------------------------------------------------------------------------------------
# Update and install the barebones essential software

yum -y install epel-release
yum -y update
yum -y install gcc python-devel python-pip git kernel-devel-$(uname -r) kernel-headers-$(uname -r) dkms
yes | pip install --upgrade pip
yes | pip install ansible