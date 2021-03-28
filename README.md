# Mower controller

This controller is separated by hexagonal layers and run like simple java application.
There are test for main services.


## Overview

The main class is named Application.
The input file is in resource package.
The output file will be stored in resource package.

The mower sensors are simple mocks with only one possible behaviour.
To have other behaviours only create new implementation of BladeAdapter and CameraAdapter and set up them into Application class.

The input and output are considered like text files.
To configure them only change Application class configuration and create new ActionReader implementation and StatusWriter implementation.
