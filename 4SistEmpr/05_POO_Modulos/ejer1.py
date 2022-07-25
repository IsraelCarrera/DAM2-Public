import sys
import os
from modulo.geometry.dinamic import *

din=dinamic()
din.loadstep("C60.xyz")
din.get(4,41)