import gzip
import xml.etree.ElementTree as ET

def parse_events(file_path):
    with gzip.open(file_path, 'rt') as f:
        tree = ET.parse(f)
        root = tree.getroot()

        movements = []
        for event in root.findall('event'):
            if event.attrib['type'] in ['actstart', 'actend', 'departure', 'arrival']:
                movements.append({
                    'time': event.attrib['time'],
                    'type': event.attrib['type'],
                    'person': event.attrib.get('person'),
                    'link': event.attrib.get('link'),
                    'x': event.attrib.get('x'),
                    'y': event.attrib.get('y'),
                    'mode': event.attrib.get('legMode')
                })
    return movements

events_file_path = './output_events.xml.gz'
movements = parse_events(events_file_path)

# Example output
for movement in movements[:10]:
    print(movement)
