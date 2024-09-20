class BranchOffice:
    
    company = "CheeseWheat Associates"
    
    sector = "Food science"

    def __init__(self, city, street, state, zip, manager):
        self.city = city
        self.street = street
        self.state = state
        self.zip = zip
        self.manager = manager

    def getManagerName(self):

        mgr = f"{self.city} Office Manager: {self.manager}"

        return mgr
    
    @classmethod
    def showClassInfo(self):

        return f"Company: {self.company}, sector: {self.sector}"
    
    @staticmethod
    def getStaticMethod():

        return "qpeoqweioqoewpqo"

a  = BranchOffice("Manhattan", "Journal","Empire", 55, "Tom")

print(BranchOffice.showClassInfo())