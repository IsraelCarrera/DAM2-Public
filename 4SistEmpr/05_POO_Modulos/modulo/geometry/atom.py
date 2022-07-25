class atom:
    def __init__(self, Z, r):
        self.Z = Z
        self.r = r
   
    def distancia(self, atomo2):
        distanciaX=(float(atomo2.r[0])-float(self.r[0]))**2
        distanciaY=(float(atomo2.r[1])-float(self.r[1]))**2
        distanciaZ=(float(atomo2.r[2])-float(self.r[2]))**2
        return (distanciaX+distanciaY+distanciaZ)**(1/2)
    
    def informa(self):
        print(self.Z)
        print(self.r)
    