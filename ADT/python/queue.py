class Queue():
    MIN_CAPACITY = 1 
    def __init__(self, max_capacity=10) -> None:
        self.length = 0
        self.rear = 0
        self.list = [None] * max(self.MIN_CAPACITY, max_capacity)

    def append(self, item):
        if self.is_full():
            raise Exception("Queue is full")
        
        self.list[self.rear] = item
        self.rear += 1
        self.length += 1
        
    def serve(self):
        if self.is_empty(): 
            raise Exception("Queue is empty")
        item = self.list[0]
        
        del self.list[0]
        self.list.append(None)
        
        self.length -= 1
        self.rear -= 1
        
        return item
        
    def __len__(self) -> int:
        """ Returns the number of elements in the queue."""
        return self.length
    
    def is_full(self):
        return len(self) == len(self.list)
    
    def is_empty(self):
        return len(self) == 0
    
    def clear(self) -> None:
        """ Clears all elements from the queue. """
        Queue.__init__(self, len(self.list))

class QueueList():
    def __init__(self) -> None:
        self.list = []

    def append(self, item):
        self.list.append(item)
        
    def serve(self):
        item = self.list[0]
        
        del self.list[0]
        
        return item
        
    def __len__(self) -> int:
        """ Returns the number of elements in the queue."""
        return len(self.list)
    
    def clear(self) -> None:
        """ Clears all elements from the queue. """
        Queue.__init__(self)
        
class StackList():
    def __init__(self) -> None:
        self.list = []

    def append(self, item):
        self.list.insert(0, item)
        
    def pop(self):
        return self.list.pop(0)
        
    def __len__(self) -> int:
        """ Returns the number of elements in the queue."""
        return len(self.list)
    
    def clear(self) -> None:
        """ Clears all elements from the queue. """
        StackList.__init__(self)
    
    def __str__(self) -> str:
        return str(self.list)
    