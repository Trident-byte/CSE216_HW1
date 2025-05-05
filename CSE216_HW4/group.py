from abc import ABC, abstractmethod
from typing import TypeVar, Generic, Set
from typing import Callable
import copy

# Defines a type variable T that can be any type
T = TypeVar('T')


class Group(ABC, Generic[T]):
    @abstractmethod
    def binaryOperation(self, one: T, other: T):
        pass

    @abstractmethod
    def identity(self):
        pass

    @abstractmethod
    def inverseOf(self, t: T):
        pass

    @abstractmethod
    def exponent(self, t: T, k: int):
        if k < 0:
            raise Exception("The exponent must be a non-negative integer value")
        return self.identity() if k == 0 else self.binaryOperation(t, k - 1)


class BijectionGroup(Group):
    functions = {}

    @staticmethod
    def findOneToOneCorrespondance(domain, codomain: Set[T], index: int):
        key = domain[index]
        dictionaries = []
        for item in domain:
            if not (item in codomain):
                if len(domain) - 1 == index:
                    return [{key: item}]
                codomain.add(item)
                partialdicts = BijectionGroup.findOneToOneCorrespondance(domain, codomain, index + 1)
                for partialDict in partialdicts:
                    partialDict[key] = item
                    dictionaries.append(partialDict)
                codomain.remove(item)
        return dictionaries

    @staticmethod
    def bijections_of(domain: Set[T]) -> Set[Callable[[T], T]]:
        domainlist = [item for item in domain]
        functions = set()
        mappings = BijectionGroup.findOneToOneCorrespondance(domainlist, set(), 0)
        for mapping in mappings:
            functions.add(lambda x, m=mapping: m[x])
        return functions

    def __init__(self, t: Set[T]):
        self.functions = BijectionGroup.bijections_of(t)

    def inverseOf(self, t: T):
        return 0

    def identity(self):
        return 0

    def binaryOperation(self, one: T, other: T):
        return 0

    def exponent(self, t: T, k: int):
        return self.identity()


def print_bijections(bijections: Set[Callable[[T], T]], a_few: Set[T]) -> None:
    for a_bijection in bijections:
        for n in a_few:
            print(f"{n} --> {a_bijection(n)}", end='; ')
        print()


if __name__ == "__main__":
    three_ints: Set[int] = {1, 2, 3}
    test_bijections: Set[Callable[[int], int]] = BijectionGroup.bijections_of(three_ints)
    print_bijections(test_bijections, three_ints)
