from modulo.geometry.step import *
from modulo.geometry.atom import *


class dinamic:
    def __init__(self):
        self.dinamic = []
    
    def get(self, num1,num2):
        for i in range(0,len(self.dinamic)):
            print(self.dinamic[i].atom[num1].distancia(self.dinamic[i].atom[num2]))

    def loadstep (self, lectura):
        text=open(lectura).readlines()
        atomos=0
        for line in text:
            lectLineas = line.split()
            if(len(lectLineas)==1):
                if(atomos!=0):
                    self.dinamic.append(steps)
                atomos= atomos+1
                steps= step()
            if(len(lectLineas)==4):
                atomito = atom(lectLineas[0],lectLineas[1:])
                steps.atom.append(atomito)
        self.dinamic.append(steps)