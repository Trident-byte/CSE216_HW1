from abc import ABC, abstractmethod
from typing import TypeVar, Generic

#Defines a type variable T that can be any type
T = TypeVar('T')

class Group(ABC, Generic[T]):
    @abstractmethod
    def binaryOperation(self, one: T, other: T):
        pass

    @abstractmethod
    def identity():
        pass

    @abstractmethod
    def inverseOf(self, t : T):
        pass
    
    @abstractmethod
    def exponent(self, t : T, k : int):
        if(k < 0):
            raise Exception("The exponenet must be a non-negative integer value")
        return identity if k == 0 else binaryOperation(t, k - 1)
    
